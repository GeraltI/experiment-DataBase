#include <stdlib.h>
#include <stdio.h>
#include "extmem.h"

int main(int argc, char **argv)
{
    Buffer buf; /* A buffer */
    unsigned char *blk; /* A pointer to a block */

    /* Initialize the buffer */
    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }

    /* Get a new block in the buffer */
    blk = getNewBlockInBuffer(&buf);

    /* Fill data into the block */
    for (int i = 0; i < 8; i++)
        *(blk + i) = 'a' + i;

    /* Write the block to the hard disk */
    if (writeBlockToDisk(blk, 8888, &buf) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }

    /* Read the block from the hard disk */
    if ((blk = readBlockFromDisk(1, &buf)) == NULL)
    {
        perror("Reading Block Failed!\n");
        return -1;
    }

    freeBlockInBuffer(blk,&buf);

    /* Process the data in the block */
    /**
    int X = -1;
    int Y = -1;
    int addr = -1;

    char str[5];
    printf("block:1\n");
    for (i = 0; i < 7; i++) //һ��blk��7��Ԫ���һ����ַ
    {

        for (int k = 0; k < 4; k++)
        {
            str[k] = *(blk + i*8 + k);
        }
        X = atoi(str);
        for (int k = 0; k < 4; k++)
        {
            str[k] = *(blk + i*8 + 4 + k);
        }
        Y = atoi(str);
        printf("(%d, %d) ", X, Y);
    }
    for (int k = 0; k < 4; k++)
    {
        str[k] = *(blk + i*8 + k);
    }
    addr = atoi(str);
    printf("\nnext address = %d \n", addr);


    printf("\n");
    printf("IO's is %d\n", buf.numIO);
    */
    /* Check the number of IO's */

    /**
    1 �������101-102
    ʵ�ֻ������������Ĺ�ϵѡ���㷨������ ExtMem ����⣬ʹ�� C ����
ʵ�����������㷨��ѡ�� S.C=128 ��Ԫ�飬��¼ IO ��д����������ѡ��
�������ڴ����ϡ���ģ��ʵ�� select S.C, S.D from S where S.C = 128��
    */
    //R
    int RBEGIN = 1;int REND = 16;
    //S
    int SBEGIN = 17;int SEND = 48;
    //R
    int A,B;
    //S
    int C,D;

    char str[8];
    int count = 0;

    printf("********************(1)********************\n");

    //����д�ļ�
    unsigned char *blk_write;
    blk_write = getNewBlockInBuffer(&buf);

    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }
    int currentGroup = 1;
    for(int num = SBEGIN; num <= SEND; num++)
    {
        printf("�������ݿ�%d\n",num);
        if ((blk = readBlockFromDisk(num, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int i = 0; i < 7; i++) //һ��blk��7��Ԫ���һ����ַ
        {
            for (int k = 0; k < 4; k++)
            {
                str[k] = *(blk + i * 8 + k);
            }
            C = atoi(str);
            for (int k = 0; k < 4; k++)
            {
                str[k] = *(blk + i * 8 + 4 + k);
            }
            D = atoi(str);
            if(C == 128)
            {
                if(count == 8)
                {
                    count = 0;
                    printf("д�����ݿ�%d\n",100 + currentGroup);
                    if (writeBlockToDisk(blk_write, 100 + currentGroup, &buf) != 0)
                    {
                        perror("Writing Block Failed!\n");
                        return -1;
                    }
                    currentGroup++;
                    blk_write = getNewBlockInBuffer(&buf);
                }
                printf("(C = %d, D = %d)\n", C, D);
                for (int k = 0; k < 8; k++)
                {
                    *(blk_write + count * 8 + k) = *(blk + i * 8 + k);
                }
                count++;
            }
        }
        freeBlockInBuffer(blk,&buf);
    }
    printf("д�����ݿ�%d\n",100 + currentGroup);
    if (writeBlockToDisk(blk_write, 100 + currentGroup, &buf) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }
    currentGroup++;

    printf("(1)��дIO����:%ld\n", buf.numIO);

    /**
    2 �Ӽ���������201-248 ����������301-348
    ʵ�����׶ζ�·�鲢�����㷨��TPMMS���������ڴ滺��������ϵ R
�� S �ֱ����򣬲��������Ľ������ڴ����ϡ�
    */

    printf("********************(2)********************\n");
    printf("*************�����Ӽ����Ӽ�����*************\n");

    /* ��R��A��������*/

    for(int num = RBEGIN; num <= REND; num++)
    {
        printf("�������ݿ�%d\n",num);
        if ((blk = readBlockFromDisk(num, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int i = 0; i < 7; i++) //һ��blk��7��Ԫ���һ����ַ
        {
            int minA = 1000;
            int minAcount = i;
            //�ҵ�����ֵ
            for(int j = i; j < 7; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    str[k] = *(blk + j * 8 + k);
                }
                int currentA = atoi(str);

                if(currentA < minA)
                {
                    minA = currentA;
                    minAcount = j;
                }
            }
            //��������ֵ�͵�i��ֵ
            char temp[8];
            for (int k = 0; k < 8; k++)
            {
                temp[k] = *(blk + i * 8 + k);
            }
            for (int k = 0; k < 8; k++)
            {
                *(blk + i * 8 + k) = *(blk + minAcount * 8 + k);
            }
            for (int k = 0; k < 8; k++)
            {
                *(blk + minAcount * 8 + k) = temp[k];
            }
            //д��ַ
            sprintf(str, "%d ", 201 + num);
            for (int k = 0; k < 4; k++)
            {
                *(blk + 56 + k) = str[k];
            }
        }
        //д��100 + num��
        printf("д�����ݿ�%d\n",200 + num);
        if (writeBlockToDisk(blk, 200 + num, &buf) != 0)
        {
            perror("Writing Block Failed!\n");
            return -1;
        }
    }

    /* ��S��C��������*/

    for(int num = SBEGIN; num <= SEND; num++)
    {
        printf("�������ݿ�%d\n",num);
        if ((blk = readBlockFromDisk(num, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int i = 0; i < 7; i++) //һ��blk��7��Ԫ���һ����ַ
        {
            int minC = 1000;
            int minCcount = i;
            //�ҵ�����ֵ
            for(int j = i; j < 7; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    str[k] = *(blk + j * 8 + k);
                }
                int currentC = atoi(str);

                if(currentC < minC)
                {
                    minC = currentC;
                    minCcount = j;
                }
            }
            //��������ֵ�͵�i��ֵ
            char temp[8];
            for (int k = 0; k < 8; k++)
            {
                temp[k] = *(blk + i * 8 + k);
            }
            for (int k = 0; k < 8; k++)
            {
                *(blk + i * 8 + k) = *(blk + minCcount * 8 + k);
            }
            for (int k = 0; k < 8; k++)
            {
                *(blk + minCcount * 8 + k) = temp[k];
            }
            //д��ַ
            sprintf(str, "%d ", 101 + num);
            for (int k = 0; k < 4; k++)
            {
                *(blk + 56 + k) = str[k];
            }
        }
        //д��100 + num��
        printf("д�����ݿ�%d\n",200 + num);
        if (writeBlockToDisk(blk, 200 + num, &buf) != 0)
        {
            perror("Writing Block Failed!\n");
            return -1;
        }
    }

    int max = 48;
    int useCount[max + 1];//���Լ�¼ÿ���������ݿ��õ��ڼ�������
    for(int i = 0; i < max + 1; i++)
    {
        useCount[i] = 0;
    }

    printf("**************���Ӽ���鲢����**************\n");

    /* ��R��A���й鲢���� */

    unsigned char *blk_plus[4];
    for(int i = 0; i < 4; i++)
    {
        blk_plus[i] = getNewBlockInBuffer(&buf);
    }
    unsigned char *blk_get;
    //��ʼ��������
    count = 0;
    for(int num = RBEGIN; num <= REND; num++)
    {
        if ((blk_get = readBlockFromDisk(num + 200, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int k = 0; k < 8; k++)
        {
            blk_plus[count / 64][count % 64 + k] = *(blk_get + k);
        }
        count += 8;
        useCount[num]++;
        freeBlockInBuffer(blk_get,&buf);
    }
    int current = 0;
    currentGroup = 1;
    blk_write = getNewBlockInBuffer(&buf);
    for(int num = 0; num < 112; num++)
    {
        int minA = 999;
        int minACount = 0;
        //�ҳ���������С����
        for(count = 0; count < 128; count += 8)
        {
            //if(useCount[(count / 4) + 1] < 8)
            for (int k = 0; k < 4; k++)
            {
                str[k] = blk_plus[count / 64][count % 64 + k];
            }
            A = atoi(str);
            if(A < minA)
            {
                minA = A;
                minACount = count;
            }
        }
        //����������С����д��blk
        for (int k = 0; k < 8; k++)
        {
            *(blk_write + current + k) = blk_plus[minACount / 64][minACount % 64 + k];
        }
        //���±�д�����С����
        if(useCount[(minACount / 8) + RBEGIN] <= 6)
        {
            //����������ݿ�ȡ
            if ((blk_get = readBlockFromDisk(((minACount / 8) + RBEGIN) + 200, &buf)) == NULL)
            {
                perror("%dReading Block Failed!\n");
                return -1;
            }
            for (int k = 0; k < 8; k++)
            {
                blk_plus[minACount / 64][minACount % 64 + k] = *(blk_get + useCount[(minACount / 8) + RBEGIN] * 8 + k);
            }
            freeBlockInBuffer(blk_get,&buf);
        }
        else
        {
            //���û�����ݿ�ȡ
            sprintf(str, "%d %d ", 999, 999);
            for (int k = 0; k < 8; k++)
            {
                blk_plus[minACount / 64][minACount % 64 + k] = str[k];
            }
        }
        useCount[(minACount / 8) + RBEGIN]++;
        current += 8;
        if(current == 56)
        {
            //д��ַ
            sprintf(str, "%d ", 301 + currentGroup);
            for (int k = 0; k < 4; k++)
            {
                *(blk_write + current + k) = str[k];
            }
            //�����ݿ�д��
            printf("д�����ݿ�%d\n",300 + currentGroup);
            if (writeBlockToDisk(blk_write, 300 + currentGroup, &buf) != 0)
            {
                perror("Writing Block Failed!\n");
                return -1;
            }
            currentGroup++;
            current = 0;
            //�ӻ�������������ݿ�
            blk_write = getNewBlockInBuffer(&buf);
        }
    }
    /* ��S��C���й鲢���� */

    //��ʼ��������
    count = 0;
    for(int num = SBEGIN; num <= SEND; num++)
    {
        if ((blk_get = readBlockFromDisk(num + 200, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int k = 0; k < 8; k++)
        {
            blk_plus[count / 64][count % 64 + k] = *(blk_get + k);
        }
        count += 8;
        useCount[num]++;
        freeBlockInBuffer(blk_get,&buf);
    }
    blk_write = getNewBlockInBuffer(&buf);
    for(int num = 0; num < 224; num++)
    {
        int minC = 999;
        int minCCount = 0;
        //�ҳ���������С����
        for(count = 0; count < 256; count += 8)
        {
            //if(useCount[(count / 4) + 1] < 8)
            for (int k = 0; k < 4; k++)
            {
                str[k] = blk_plus[count / 64][count % 64 + k];
            }
            C = atoi(str);
            if(C < minC)
            {
                minC = C;
                minCCount = count;
            }
        }
        //����������С����д��blk
        for (int k = 0; k < 8; k++)
        {
            *(blk_write + current + k) = blk_plus[minCCount / 64][minCCount % 64 + k];
        }
        //���±�д�����С����
        if(useCount[(minCCount / 8) + SBEGIN] <= 6)
        {
            //����������ݿ�ȡ
            if ((blk_get = readBlockFromDisk(((minCCount / 8) + SBEGIN) + 200, &buf)) == NULL)
            {
                perror("Reading Block Failed!\n");
                return -1;
            }
            for (int k = 0; k < 8; k++)
            {
                blk_plus[minCCount / 64][minCCount % 64 + k] = *(blk_get + useCount[(minCCount / 8) + SBEGIN] * 8 + k);
            }
            freeBlockInBuffer(blk_get,&buf);
        }
        else
        {
            //���û�����ݿ�ȡ
            sprintf(str, "%d %d ", 999, 999);
            for (int k = 0; k < 8; k++)
            {
                blk_plus[minCCount / 64][minCCount % 64 + k] = str[k];
            }
        }
        useCount[(minCCount / 8) + SBEGIN]++;
        current += 8;
        if(current == 56)
        {
            //д��ַ
            sprintf(str, "%d ", 301 + currentGroup);
            for (int k = 0; k < 4; k++)
            {
                *(blk_write + current + k) = str[k];
            }
            //�����ݿ�д��
            printf("д�����ݿ�%d\n",300 + currentGroup);
            if (writeBlockToDisk(blk_write, 300 + currentGroup, &buf) != 0)
            {
                perror("Writing Block Failed!\n");
                return -1;
            }
            currentGroup++;
            current = 0;
            //�ӻ�������������ݿ�
            blk_write = getNewBlockInBuffer(&buf);
        }
    }

    //��ջ�����
    for(int i = 0; i < 4; i++)
    {
        freeBlockInBuffer(blk_plus[i],&buf);
    }
    freeBlockInBuffer(blk_get,&buf);
    freeBlockInBuffer(blk_write,&buf);
    freeBlockInBuffer(blk,&buf);



    /**
    3 R����A��������351-352 S����C��������353-356 ���д��121-122
    ʵ�ֻ��������Ĺ�ϵѡ���㷨�����ã�2���е�������Ϊ��ϵ S ������
���ļ������������ļ�ѡ�� S.C=128 ��Ԫ�飬����ѡ��������ڴ���
�ϡ���¼ IO ��д�������루1���еĽ���Աȡ���ģ��ʵ�� select S.C, S.D
from S where S.C = 128 ��
    */
    printf("********************(3)********************\n");

    //ΪR.A���������飬ÿ���������СΪ64�ֽڣ�ÿ����������Aֵ��С(4�ֽ�)�Ͷ�Ӧ���λ��(4�ֽ�)��ÿ������8�ֽڴ�С
    //����ÿ��������������R����һ�������ݿ�(1��������Ӧ7������)
    //��R.A��Ҫ16��������1����������Դ��64/8=8��������������Ҫ2�������飬��S��������Ҫ4��������
    //����351-352ΪR.A������
    //����353-356ΪS.C������

    blk = getNewBlockInBuffer(&buf);
    current = 0;
    currentGroup = 1;
    for(int num = RBEGIN; num <= REND; num++)
    {
        //����ÿ�����ݿ��1λ
        if ((blk_get = readBlockFromDisk(num + 300, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int k = 0; k < 4; k++)
        {
            *(blk + current + k) = *(blk_get + k);
        }
        freeBlockInBuffer(blk_get,&buf);

        //д��ַ
        sprintf(str, "%d ", num + 300);
        for (int k = 0; k < 4; k++)
        {
            *(blk + current + 4 + k) = str[k];
        }
        current += 8;
        //д�����ݿ�
        if(current == 64)
        {
            current = 0;
            //�����ݿ�д��
            printf("д��������%d\n",350 + currentGroup);
            if (writeBlockToDisk(blk, 350 + currentGroup, &buf) != 0)
            {
                perror("Writing Block Failed!\n");
                return -1;
            }
            blk = getNewBlockInBuffer(&buf);
            currentGroup++;
        }
    }

    blk = getNewBlockInBuffer(&buf);
    for(int num = SBEGIN; num <= SEND; num++)
    {
        //����ÿ�����ݿ��1λ
        if ((blk_get = readBlockFromDisk(num + 300, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for (int k = 0; k < 4; k++)
        {
            *(blk + current + k) = *(blk_get + k);
        }
        freeBlockInBuffer(blk_get,&buf);

        //д��ַ
        sprintf(str, "%d ", num + 300);
        for (int k = 0; k < 4; k++)
        {
            *(blk + current + 4 + k) = str[k];
        }
        current += 8;
        //д�����ݿ�
        if(current == 64)
        {
            current = 0;
            //�����ݿ�д��
            printf("д��������%d\n",350 + currentGroup);
            if (writeBlockToDisk(blk, 350 + currentGroup, &buf) != 0)
            {
                perror("Writing Block Failed!\n");
                return -1;
            }
            blk = getNewBlockInBuffer(&buf);
            currentGroup++;
        }
    }
    freeBlockInBuffer(blk,&buf);
    //��ʼ��������
    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }
    //��353-356��������Ѱ��
    int find = 0;
    int beginaddr = 317;
    int endaddr = 348;
    for(count = 0; count < 256; count += 8)
    {
        if(count % 64 == 0)
        {
            freeBlockInBuffer(blk,&buf);
            printf("����������%d\n",count / 64 + 353);
            if ((blk = readBlockFromDisk(count / 64 + 353, &buf)) == NULL)
            {
                perror("Reading Block Failed!\n");
                return -1;
            }
        }
        for(int k = 0; k < 4; k++)
        {
            str[k] = *(blk + count % 64 + k);
        }
        C = atoi(str);
        for(int k = 0; k < 4; k++)
        {
            str[k] = *(blk + count % 64 + 4 + k);
        }
        int addr;
        addr = atoi(str);
        if(C < 128)
        {
            beginaddr = addr;
        }
        else if(C >= 128)
        {
            if(C == 128)
            {
                endaddr = addr;
                find = 1;
            }
            else if(find == 0)
            {
                endaddr = addr - 1;
                find = 1;
            }
        }
    }
    blk_write = getNewBlockInBuffer(&buf);
    //�����������ҵ�beginaddr��endaddr��ΪҪ��Ѱ�ķ�Χ
    current = 0;
    currentGroup = 1;
    for(int num = beginaddr; num <= endaddr; num++)
    {
        freeBlockInBuffer(blk,&buf);
        printf("�������ݿ�%d\n",num);
        if ((blk = readBlockFromDisk(num, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(count = 0; count < 56; count += 8)
        {
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk + count + k);
            }
            C = atoi(str);
            if(C == 128)
            {
                if(current == 8)
                {
                    current = 0;
                    printf("д�����ݿ�%d\n",120 + currentGroup);
                    if (writeBlockToDisk(blk_write, 120 + currentGroup, &buf) != 0)
                    {
                        perror("Writing Block Failed!\n");
                        return -1;
                    }
                    currentGroup++;
                    freeBlockInBuffer(blk_write,&buf);
                    blk_write = getNewBlockInBuffer(&buf);
                }
                for (int k = 0; k < 8; k++)
                {
                    *(blk_write + current * 8 + k) = *(blk + count + k);
                }
                current++;
            }
        }
    }
    printf("д�����ݿ�%d\n",120 + currentGroup);
    if (writeBlockSizeToDisk(blk_write, 120 + currentGroup, &buf, current * 8) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }

    printf("(2)��дIO����:%ld\n", buf.numIO);
    freeBlockInBuffer(blk,&buf);
    /**
    4 �����401-498
    ʵ�ֻ�����������Ӳ����㷨��Sort-Merge-Join�����Թ�ϵ S �� R ����
S.C ���� R.A ����ͳ�����Ӵ����������ӽ������ڴ����ϡ� ��ģ��
ʵ�� select S.C, S.D, R.A, R.B from S inner join R on S.C = R.A��
    */
    printf("********************(4)********************\n");
    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }
    unsigned char *blk_A;
    unsigned char *blk_C;
    blk_write = getNewBlockInBuffer(&buf);
    int relationCount = 0;
    current = 0;
    currentGroup = 1;
    for(int numA = RBEGIN + 300; numA <= REND + 300; numA++ )
    {
        if ((blk_A = readBlockFromDisk(numA, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(int countA = 0; countA < 56; countA += 8)
        {
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_A + countA + k);
            }
            A = atoi(str);
            //��ȡC
            for(int numC = SBEGIN + 300; numC <= SEND + 300; numC++ )
            {
                if ((blk_C = readBlockFromDisk(numC, &buf)) == NULL)
                {
                    perror("Reading Block Failed!\n");
                    return -1;
                }
                for(int countC = 0; countC < 56; countC += 8)
                {
                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_C + countC + k);
                    }
                    C = atoi(str);
                    if(A == C)
                    {
                        relationCount++;
                        //дS
                        for(int k = 0; k < 8; k++)
                        {
                            *(blk_write + current) = *(blk_C + countC + k);
                            current++;
                        }
                        //дR
                        for(int k = 0; k < 8; k++)
                        {
                            *(blk_write + current) = *(blk_A + countA + k);
                            current++;
                        }
                    }
                    if(current == 64)
                    {
                        current = 0;
                        //�����ݿ�д��
                        printf("д�����ݿ�%d\n",400 + currentGroup);
                        if (writeBlockToDisk(blk_write, 400 + currentGroup, &buf) != 0)
                        {
                            perror("Writing Block Failed!\n");
                            return -1;
                        }
                        blk_write = getNewBlockInBuffer(&buf);
                        currentGroup++;
                    }
                }
                //�ͷ�C��ȡ��
                freeBlockInBuffer(blk_C,&buf);
            }
        }
        //�ͷ�A��ȡ��
        freeBlockInBuffer(blk_A,&buf);
    }
    //�����ݿ�д��
    printf("д�����ݿ�%d\n",400 + currentGroup);
    if (writeBlockSizeToDisk(blk_write, 400 + currentGroup, &buf, current) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }
    printf("R��S���Ӽ�����:%d\n",relationCount);
    /**
    5 ����801-841 ����141-142 �901-927
    ʵ�ֻ��������ɢ�е�����ɨ���㷨��ʵ�ֲ���S R��������S R����
�S - R������һ�ּ��ϲ����㷨�����������ڴ����ϣ���ͳ�Ʋ���
������������Ԫ�������
    */
    printf("********************(5)********************\n");
    printf("********************����********************\n");
    //����
    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }

    //R
    blk_write = getNewBlockInBuffer(&buf);
    relationCount = 0;
    current = 0;
    currentGroup = 1;
    for(int numA = RBEGIN + 300; numA <= REND + 300; numA++ )
    {
        if ((blk_A = readBlockFromDisk(numA, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(int countA = 0; countA < 56; countA += 8)
        {
            relationCount++;
            for(int k = 0; k < 8; k++)
            {
                *(blk_write + current) = *(blk_A + countA + k);
                current++;
            }
            if(current == 64)
            {
                current = 0;
                //�����ݿ�д��
                printf("д�����ݿ�%d\n",800 + currentGroup);
                if (writeBlockToDisk(blk_write, 800 + currentGroup, &buf) != 0)
                {
                    perror("Writing Block Failed!\n");
                    return -1;
                }
                blk_write = getNewBlockInBuffer(&buf);
                currentGroup++;
            }
        }
        //�ͷ�A��ȡ��
        freeBlockInBuffer(blk_A,&buf);
    }
    //S
    for(int numC = SBEGIN + 300; numC <= SEND + 300; numC++ )
    {
        if ((blk_C = readBlockFromDisk(numC, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(int countC = 0; countC < 56; countC += 8)
        {
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + k);
            }
            C = atoi(str);
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + 4 + k);
            }
            D = atoi(str);
            find = 0;
            for(int numA = RBEGIN + 300; numA <= REND + 300; numA++ )
            {
                if ((blk_A = readBlockFromDisk(numA, &buf)) == NULL)
                {
                    perror("Reading Block Failed!\n");
                    return -1;
                }
                for(int countA = 0; countA < 56; countA += 8)
                {

                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + k);
                    }
                    A = atoi(str);
                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + 4 + k);
                    }
                    B = atoi(str);
                    if(A == C && B == D)
                    {
                        find = 1;
                    }
                }
                //�ͷ�A��ȡ��
                freeBlockInBuffer(blk_A,&buf);
            }
            if(find == 0)
            {
                relationCount++;
                for(int k = 0; k < 8; k++)
                {
                    *(blk_write + current) = *(blk_C + countC + k);
                    current++;
                }
                if(current == 64)
                {
                    current = 0;
                    //�����ݿ�д��
                    printf("д�����ݿ�%d\n",800 + currentGroup);
                    if (writeBlockToDisk(blk_write, 800 + currentGroup, &buf) != 0)
                    {
                        perror("Writing Block Failed!\n");
                        return -1;
                    }
                    blk_write = getNewBlockInBuffer(&buf);
                    currentGroup++;
                }
            }
        }
        //�ͷ�C��ȡ��
        freeBlockInBuffer(blk_C,&buf);
    }
    //�����ݿ�д��
    printf("д�����ݿ�%d\n",800 + currentGroup);
    if (writeBlockSizeToDisk(blk_write, 800 + currentGroup, &buf, current) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }
    printf("R��S��������:%d\n",relationCount);
    printf("********************����********************\n");
    //����

    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }

    blk_write = getNewBlockInBuffer(&buf);
    relationCount = 0;
    current = 0;
    currentGroup = 1;
    for(int numC = SBEGIN + 300; numC <= SEND + 300; numC++ )
    {
        if ((blk_C = readBlockFromDisk(numC, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(int countC = 0; countC < 56; countC += 8)
        {
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + k);
            }
            C = atoi(str);
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + 4 + k);
            }
            D = atoi(str);
            for(int numA = RBEGIN + 300; numA <= REND + 300; numA++ )
            {
                if ((blk_A = readBlockFromDisk(numA, &buf)) == NULL)
                {
                    perror("Reading Block Failed!\n");
                    return -1;
                }
                for(int countA = 0; countA < 56; countA += 8)
                {

                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + k);
                    }
                    A = atoi(str);
                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + 4 + k);
                    }
                    B = atoi(str);
                    if(A == C && B == D)
                    {
                        relationCount++;
                        for(int k = 0; k < 8; k++)
                        {
                            *(blk_write + current) = *(blk_C + countC + k);
                            current++;
                        }
                        if(current == 64)
                        {
                            current = 0;
                            //�����ݿ�д��
                            printf("д�����ݿ�%d\n",140 + currentGroup);
                            if (writeBlockToDisk(blk_write, 140 + currentGroup, &buf) != 0)
                            {
                                perror("Writing Block Failed!\n");
                                return -1;
                            }
                            blk_write = getNewBlockInBuffer(&buf);
                            currentGroup++;
                        }
                    }
                }
                //�ͷ�A��ȡ��
                freeBlockInBuffer(blk_A,&buf);
            }
        }
        //�ͷ�C��ȡ��
        freeBlockInBuffer(blk_C,&buf);
    }
    //�����ݿ�д��
    printf("д�����ݿ�%d\n",140 + currentGroup);
    if (writeBlockSizeToDisk(blk_write, 140 + currentGroup, &buf, current) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }
    printf("R��S��������:%d\n",relationCount);
    printf("********************�********************\n");
    //�

    if (!initBuffer(520, 64, &buf))
    {
        perror("Buffer Initialization Failed!\n");
        return -1;
    }

    blk_write = getNewBlockInBuffer(&buf);
    relationCount = 0;
    current = 0;
    currentGroup = 1;
    for(int numC = SBEGIN + 300; numC <= SEND + 300; numC++ )
    {
        if ((blk_C = readBlockFromDisk(numC, &buf)) == NULL)
        {
            perror("Reading Block Failed!\n");
            return -1;
        }
        for(int countC = 0; countC < 56; countC += 8)
        {
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + k);
            }
            C = atoi(str);
            for(int k = 0; k < 4; k++)
            {
                str[k] = *(blk_C + countC + 4 + k);
            }
            D = atoi(str);
            find = 0;
            for(int numA = RBEGIN + 300; numA <= REND + 300; numA++ )
            {
                if ((blk_A = readBlockFromDisk(numA, &buf)) == NULL)
                {
                    perror("Reading Block Failed!\n");
                    return -1;
                }
                for(int countA = 0; countA < 56; countA += 8)
                {

                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + k);
                    }
                    A = atoi(str);
                    for(int k = 0; k < 4; k++)
                    {
                        str[k] = *(blk_A + countA + 4 + k);
                    }
                    B = atoi(str);
                    if(A == C && B == D)
                    {
                        find = 1;
                    }
                }
                //�ͷ�A��ȡ��
                freeBlockInBuffer(blk_A,&buf);
            }
            if(find == 0)
            {
                relationCount++;
                for(int k = 0; k < 8; k++)
                {
                    *(blk_write + current) = *(blk_C + countC + k);
                    current++;
                }
                if(current == 64)
                {
                    current = 0;
                    //�����ݿ�д��
                    printf("д�����ݿ�%d\n",900 + currentGroup);
                    if (writeBlockToDisk(blk_write, 900 + currentGroup, &buf) != 0)
                    {
                        perror("Writing Block Failed!\n");
                        return -1;
                    }
                    blk_write = getNewBlockInBuffer(&buf);
                    currentGroup++;
                }
            }
        }
        //�ͷ�C��ȡ��
        freeBlockInBuffer(blk_C,&buf);
    }

    //�����ݿ�д��
    printf("д�����ݿ�%d\n",900 + currentGroup);
    if (writeBlockSizeToDisk(blk_write, 900 + currentGroup, &buf, current) != 0)
    {
        perror("Writing Block Failed!\n");
        return -1;
    }
    printf("R��S�����:%d\n",relationCount);
}

